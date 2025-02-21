package jose.learning_api_spring.service.impl;

import jose.learning_api_spring.domain.model.User;
import jose.learning_api_spring.domain.repository.UserRepository;
import jose.learning_api_spring.service.UserService;
import jose.learning_api_spring.service.exception.BusinessException;
import jose.learning_api_spring.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class UserServiceImpl implements UserService {

    private static final Long UNCHANGEABLE_USER_ID = 1L;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public User create(User userToCreate) {
        ofNullable(userToCreate).orElseThrow(() -> new BusinessException("User to create must not be null."));
        ofNullable(userToCreate.getEmail()).orElseThrow(() -> new BusinessException("User email must not be null."));

        this.validateChangeableId(userToCreate.getId(), "created");
        if (userRepository.existsByEmail(userToCreate.getEmail())) {
            throw new BusinessException("This email is already in use.");
        }
        return this.userRepository.save(userToCreate);
    }

    @Transactional
    public User update(Long id, User userToUpdate) {
        this.validateChangeableId(id, "updated");
        User dbUser = this.findById(id);

        if (!dbUser.getId().equals(userToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbUser.setName(userToUpdate.getName());
        dbUser.setEmail(userToUpdate.getEmail());
        dbUser.setPassword(userToUpdate.getPassword());
        dbUser.setWallets(userToUpdate.getWallets());

        return this.userRepository.save(dbUser);
    }

    @Transactional
    public void delete(Long id) {
        this.validateChangeableId(id, "deleted");
        User dbUser = this.findById(id);
        this.userRepository.delete(dbUser);
    }

    private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_USER_ID.equals(id)) {
            throw new BusinessException("User with ID %d cannot be %s.".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }
}
