package modules;

public interface User {

    void createAccount();

    void login();

    void registerAsLearner();

    void registerAsInstructor();

    void getLearnerByEmail();

    void getInstructorByEmail();
}
