// java -Dfile.encoding=UTF8 classname
import java.util.stream.Stream;
import java.util.Iterator;

class Parser implements ControllerInterface {
    private TaskModelInterface model;
    private Ui display;
    private TaskCreator taskCreator;

    public Parser(TaskModelInterface model) {
        this.model = model;
        //this.display = new Display(this, model);
        this.display = new Ui(this, model);
        this.taskCreator = new BasicTaskCreator();
    }

    public void start() {
        this.display.instance();
    }

    public void stop() {
        //this.display.
    }

    public void whatsGoingOn(String command) {
        String[] commandlist = command.split(" ");
        if (commandlist[0].toUpperCase().equals("LIST")) {
            this.listTasks();
        } else if 
            (commandlist[0].toUpperCase().equals("DONE")) {
            this.doneTask(command);
        } else if 
            (commandlist[0].toUpperCase().equals("DELETE")) {
            this.deleteTask(command);
        } else {
            this.addTask(command);
        }
    }

    public boolean isEndCommand(String cmd) {
        String[] cmdlist = cmd.split(" ");
        return cmdlist[0].toUpperCase().equals("BYE");
    }

    public void addTask(String command) {

        try {
            TaskInterface task = taskCreator.createTask(command);
            this.model.addTask(task);
            this.display.printAddTaskSection(task.toString(), 
                    this.model.getTotalTasks());
                        
        } catch (OWOException ex) {
            this.display.printErrorSection(ex.getMessage());
        }            
                
    }

    public void doneTask(String command) {
        String[] commandlist = command.split(" ");
        /* check for exceptions */
        Integer taskNum = Integer.valueOf(commandlist[1]);
        TaskInterface task = this.model.doneTask(taskNum);
        this.display.printDoneTaskSection(task.toString());
    }

    public void deleteTask(String command) {
        String[] commandlist = command.split(" ");
        /* check for exceptions */
        Integer taskNum = Integer.valueOf(commandlist[1]);
        TaskInterface task = this.model.deleteTask(taskNum);
        this.display.printDeleteTaskSection(task.toString(), 
                this.model.getTotalTasks());
    }

    public void listTasks() {
        Stream<TaskInterface> taskStream = 
            this.model.getTaskStream();

        //this.display.printAllTasks(iter);
        this.display.printAllTasks(taskStream);
    }
}
