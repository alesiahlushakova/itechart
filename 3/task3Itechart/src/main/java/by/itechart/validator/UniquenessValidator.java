package main.java.by.itechart.validator;


import java.io.File;

public class UniquenessValidator implements Validator{

    @Override
    public boolean validate(File fileItem) {
        return fileItem.exists();
    }
}
