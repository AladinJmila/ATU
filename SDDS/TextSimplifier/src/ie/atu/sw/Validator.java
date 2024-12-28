package ie.atu.sw;

@FunctionalInterface
interface Prompter {
    void prompt();
}

public interface Validator<T> {
    public T validate(Prompter showPrompt, T[] range);
}
