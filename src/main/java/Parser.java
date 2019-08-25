// java -Dfile.encoding=UTF8 classname
import java.util.stream.Stream;
import java.util.Iterator;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.lang.String;
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
        } else if 
            (commandlist[0].toUpperCase().equals("FIND")) {
            this.findTasks(command);
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
//        Iterator<TaskInterface> iter = 
//            this.model.getTaskListIterator();
        Stream<TaskInterface> taskStream = 
            this.model.getTaskStream();

        //this.display.printAllTasks(iter);
        this.display.printAllTasks(taskStream);
    }

    public void findTasks(String command) {
        String[] cmdList = command.split(" ");
        if (cmdList.length <= 1 ) {
            this.listTasks();
            return;
        }

        List<String> cmdxs = 
            new LinkedList<String>(Arrays.asList(cmdList));
        cmdxs.remove(0);
        //String searchTerm = String.join(" ",cmdxs.toArray());
        String searchTerm = String.join(" ",cmdxs);


        Stream<TaskInterface> taskStream = 
            this.model.getTaskStream();

        Stream<String> filteredStream = taskStream
            .map(x -> x.toString())
            .filter(x -> x.contains(searchTerm)); 
        this.display.printAllTasks(filteredStream);
    }

}
