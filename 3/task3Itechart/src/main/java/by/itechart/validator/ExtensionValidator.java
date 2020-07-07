package main.java.by.itechart.validator;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class ExtensionValidator implements Validator {
    @Override
    public boolean validate(File fileItem) {
        String extension = FilenameUtils.getExtension(fileItem.getName());
        return extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg")
                || extension.equalsIgnoreCase("gif");
    }
}
