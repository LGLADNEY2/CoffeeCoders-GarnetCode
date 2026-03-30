package com.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class StudentTest {

	@Test
	public void constructorWithUsernameAndPassword_setsRoleToStudent() {
		Student student = new Student("studentUser", "studentPass");

		assertEquals(Role.STUDENT, student.getRole());
	}

	@Test
	public void addTrustedRole_withValidTag_addsOneTrustedRole() {
		Student student = createStudentWithEmptyLists();
		QuestionTag trustedTag = createTag(Category.CLASS, Language.JAVA, Course.CSCE_247);

		student.addTrustedRole(trustedTag);

		assertEquals(1, student.getTrustedRoles().size());
	}

	@Test
	public void removeTrustedRole_whenTagExists_returnsTrue() {
		Student student = createStudentWithEmptyLists();
		QuestionTag trustedTag = createTag(Category.INTERVIEW, Language.CPP, Course.CSCE_240);
		student.addTrustedRole(trustedTag);

		boolean removed = student.removeTrustedRole(trustedTag);

		assertTrue(removed);
	}

	@Test
	public void removeTrustedRole_whenTagDoesNotExist_returnsFalse() {
		Student student = createStudentWithEmptyLists();
		QuestionTag existingTag = createTag(Category.CLASS, Language.JAVA, Course.CSCE_247);
		QuestionTag missingTag = createTag(Category.SOURCE, Language.PYTHON, Course.CSCE_146);
		student.addTrustedRole(existingTag);

		boolean removed = student.removeTrustedRole(missingTag);

		assertFalse(removed);
	}

	@Test
	public void addUserQuestion_whenQuestionMatchesTrustedTag_returnsTrue() {
		Student student = createStudentWithEmptyLists();
		QuestionTag trustedTag = createTag(Category.CLASS, Language.JAVA, Course.CSCE_247);
		student.addTrustedRole(trustedTag);

		Question question = createQuestionWithTag(createTag(Category.CLASS, Language.JAVA, Course.CSCE_247));

		boolean added = student.addUserQuestion(question, student.getTrustedRoles());

		assertTrue(added);
	}

	@Test
	public void addUserQuestion_whenQuestionDoesNotMatchTrustedTag_returnsFalse() {
		Student student = createStudentWithEmptyLists();
		QuestionTag trustedTag = createTag(Category.CLASS, Language.JAVA, Course.CSCE_247);
		student.addTrustedRole(trustedTag);

		Question question = createQuestionWithTag(createTag(Category.INTERVIEW, Language.CPP, Course.CSCE_240));

		boolean added = student.addUserQuestion(question, student.getTrustedRoles());

		assertFalse(added);
	}

	@Test
	public void removeUserQuestion_whenQuestionExists_returnsTrue() {
		Student student = createStudentWithEmptyLists();
		Question question = createQuestionWithTag(createTag(Category.CLASS, Language.JAVA, Course.CSCE_247));
		student.getUserQuestions().add(question);

		boolean removed = student.removeUserQuestion(question.getQuestionID());

		assertTrue(removed);
	}

	@Test
	public void removeUserQuestion_whenQuestionDoesNotExist_returnsFalse() {
		Student student = createStudentWithEmptyLists();
		Question question = createQuestionWithTag(createTag(Category.CLASS, Language.JAVA, Course.CSCE_247));
		student.getUserQuestions().add(question);

		boolean removed = student.removeUserQuestion(UUID.randomUUID());

		assertFalse(removed);
	}

	@Test
	public void updateDailyStreak_whenLoginIsMoreThanOneDayLater_setsStreakToTwo() {
		Student student = createStudentWithEmptyLists();
		student.setDailyStreak(5);

		Calendar calendar = Calendar.getInstance();
		calendar.set(2026, Calendar.MARCH, 1, 8, 0, 0);
		Date previousLogin = calendar.getTime();
		student.setLastLogin(previousLogin);

		calendar.set(2026, Calendar.MARCH, 4, 8, 0, 0);
		Date newLogin = calendar.getTime();

		student.updateDailyStreak(newLogin);

		assertEquals(2, student.getDailyStreak());
	}

	@Test
	public void addFavoriteQuestion_withNullId_keepsFavoriteQuestionListEmpty() {
		Student student = createStudentWithEmptyLists();

		student.addFavoriteQuestion(null);

		assertTrue(student.getFavoriteQuestions().isEmpty());
	}

	@Test
	public void addCompletedQuestion_withNullId_keepsCompletedQuestionListEmpty() {
		Student student = createStudentWithEmptyLists();

		student.addCompletedQuestion(null);

		assertTrue(student.getCompletedQuestions().isEmpty());
	}

	@Test
	public void constructorWithUsernameAndPassword_leavesFavoriteQuestionsUninitialized() {
		Student student = new Student("minimalUser", "minimalPass");

		assertNull(student.getFavoriteQuestions());
	}

	@Test(expected = NullPointerException.class)
	public void addFavoriteQuestion_withBasicConstructorAndNonNullId_throwsNullPointerException() {
		Student student = new Student("minimalUser", "minimalPass");

		student.addFavoriteQuestion(UUID.randomUUID());
	}

	@Test
	public void updateDailyStreak_whenLoginIsExactlyNextDay_incrementsStreakByOne() {
		Student student = createStudentWithEmptyLists();
		student.setDailyStreak(7);

		Calendar calendar = Calendar.getInstance();
		calendar.set(2026, Calendar.MARCH, 8, 9, 30, 0);
		Date previousLogin = calendar.getTime();
		student.setLastLogin(previousLogin);

		calendar.set(2026, Calendar.MARCH, 9, 9, 30, 0);
		Date newLogin = calendar.getTime();

		student.updateDailyStreak(newLogin);

		assertEquals(8, student.getDailyStreak());
	}

	@Test
	public void updateDailyStreak_whenLoginIsSameDay_incrementsStreakByOne() {
		Student student = createStudentWithEmptyLists();
		student.setDailyStreak(2);

		Calendar calendar = Calendar.getInstance();
		calendar.set(2026, Calendar.APRIL, 12, 14, 0, 0);
		Date sameDayLogin = calendar.getTime();
		student.setLastLogin(sameDayLogin);

		student.updateDailyStreak(sameDayLogin);

		assertEquals(3, student.getDailyStreak());
	}

	private Student createStudentWithEmptyLists() {
		Student student = new Student(
			UUID.randomUUID(),
			"First",
			"Last",
			"student@test.com",
			"studentUser",
			"studentPass",
			Role.STUDENT,
			1,
			new ArrayList<>(),
			new ArrayList<>(),
			new ArrayList<>(),
			new ArrayList<>()
		);
		assertNotNull(student.getFavoriteQuestions());
		assertNotNull(student.getCompletedQuestions());
		assertNotNull(student.getTrustedRoles());
		assertNotNull(student.getUserQuestions());
		return student;
	}

	private QuestionTag createTag(Category category, Language language, Course course) {
		ArrayList<Category> categories = new ArrayList<>();
		categories.add(category);

		ArrayList<Language> languages = new ArrayList<>();
		languages.add(language);

		ArrayList<Course> courses = new ArrayList<>();
		courses.add(course);

		return new QuestionTag(categories, languages, courses);
	}

	private Question createQuestionWithTag(QuestionTag questionTag) {
		return new Question(
			UUID.randomUUID(),
			"Sample question",
			Difficulty.BEGINNER,
			new ArrayList<>(),
			questionTag,
			new ArrayList<>()
		);
	}
}
