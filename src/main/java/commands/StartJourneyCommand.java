package commands;

public class StartJourneyCommand {
    private final SwipeCardInCommand swipeCardInCommand;

    public StartJourneyCommand(SwipeCardInCommand swipeCardInCommand) {
        this.swipeCardInCommand = swipeCardInCommand;
    }

    public void run(String userName) {
        swipeCardInCommand.run(userName);
    }
}
