package by.itechart.command;


public enum CommandType {

    /**
     * commands.
     */
    UPLOAD {
        {
            this.command = new UploadCommand();
        }
    },
    DOWNLOAD {
        {
            this.command = new DownloadCommand();
        }
    };


    /**
     * Current command.
     */
    Command command;


    public Command getCurrentCommand() {
        return command;
    }
}
