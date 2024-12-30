package ie.atu.sw;

/**
 * A functional interface that defines a method for displaying prompts.
 */
@FunctionalInterface
interface Prompter {
    void prompt();
}

/**
 * A generic interface that provides validation functionality.
 *
 * @param <T> the type of elements in the range array
 * @param <R> the return type of the validation
 */
public interface Validator<T, R> {
    /**
     * Validates input based on a given range of values.
     *
     * @param showPrompt a Prompter instance to display prompts during validation
     * @param range      an array of valid values to check against
     * @return the validated result of type R
     */
    public R validate(Prompter showPrompt, T[] range);
}
