package main.java.by.itechart.validator;

import org.apache.commons.fileupload.FileItem;

import java.io.File;


public interface Validator {
boolean validate(File fileItem);
}
