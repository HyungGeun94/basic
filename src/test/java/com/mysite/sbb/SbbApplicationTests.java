package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;


	@Test
	void testJpa(){
		Question q1 = new Question();
		q1.setSubject("sb가 무엇인가요");
		q1.setContent("sb에 대해 알고싶어요");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);
	}

	@Test
	void testJpa2(){
		List<Question> all = questionRepository.findAll();
		assertEquals(3, all.size());

		Question q = all.get(0);
		assertEquals("sb가 무엇인가요",q.getSubject());

	}

	@Test
	void testJpa3(){
		Optional<Question> oq = questionRepository.findById(1);
		if(oq.isPresent()){
			Question q = oq.get();
			assertEquals("sb가 무엇인가요",q.getSubject());
		}
	}

	@Test
	void testJpa4(){
		Question q = questionRepository.findBySubject("sb가 무엇인가요");
		assertEquals(1,q.getId());

	}

	@Test
	void testJpa5() {
		List<Question> qList = this.questionRepository.findBySubjectLike("sb%");
		Question q = qList.get(0);
		assertEquals("sb가 무엇인가요", q.getSubject());
	}

	@Test
	void testJpa6(){
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		questionRepository.save(q);

	}

	@Test
	void testJpa7(){
		assertEquals(3,questionRepository.count());
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		questionRepository.delete(q);
		assertEquals(2,questionRepository.count());
	}

	@Test
	void testJpa8(){
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());

		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다");
		a.setQuestion(q);
		a.setCreateDate(LocalDateTime.now());
		answerRepository.save(a);
	}

	@Test
	void testJpa9(){
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());

	}

	@Transactional
	@Test
	void testJpa10(){
		Optional<Question> oq = questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1,answerList.size());


	}




}
