package et.com.act.microfinance.services;

import et.com.act.microfinance.models.Status;
import et.com.act.microfinance.repos.StatusRepo;
import et.com.act.microfinance.utils.Statuses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepo statusRepo;

    public void create(String name) {
        if (statusRepo.findByName(name) == null) {
            Status status = new Status();
            status.setName(name);
            statusRepo.save(status);
        }
    }

    public void onInit() {
        Arrays.stream(Statuses.values())
                .forEach(statuses -> create(statuses.getName().toUpperCase()));
    }

    public Status findById(long id) {
        return statusRepo.findById(id);
    }

    public List<Status> findAll() {
        return statusRepo.findAll();
    }
}
