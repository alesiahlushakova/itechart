package main.java.by.itechart.validator;




public class ValidatorFactory {

    private static final String EXTENSION_VALIDATOR="EXTENSION";
    private static final String SIZE_VALIDATOR="SIZE";
    private static final String UNIQUENESS_VALIDATOR="UNIQUENESS";

    public Validator defineValidator(String validator) {

        if (validator == null || validator.isEmpty()) {

            return null;
        }
        if(validator.equalsIgnoreCase(EXTENSION_VALIDATOR)){
            return new ExtensionValidator();

        } else if(validator.equalsIgnoreCase(SIZE_VALIDATOR)){
            return new SizeValidator();

        } else if(validator.equalsIgnoreCase(UNIQUENESS_VALIDATOR)){
            return new UniquenessValidator();
        }
        return null;
    }

}
