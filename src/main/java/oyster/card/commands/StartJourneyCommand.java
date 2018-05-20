package oyster.card.commands;

import oyster.card.models.Fare;
import oyster.card.repositories.FareRepository;

import static java.util.Comparator.comparing;

public class StartJourneyCommand {
    private final UpdateCardBalanceCommand updateCardBalanceCommand;
    private final FareRepository fareRepository;

    public StartJourneyCommand(UpdateCardBalanceCommand updateCardBalanceCommand, FareRepository fareRepository) {
        this.updateCardBalanceCommand = updateCardBalanceCommand;
        this.fareRepository = fareRepository;
    }

    public void run(String userName) {
        fareRepository.list()
                .stream()
                .max(comparing(Fare::getValue))
                .ifPresent(f -> updateCardBalanceCommand.run(userName, f.getValue().negate()));
    }
}
