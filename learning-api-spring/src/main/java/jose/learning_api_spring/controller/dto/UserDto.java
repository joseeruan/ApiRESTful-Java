package jose.learning_api_spring.controller.dto;

import jose.learning_api_spring.domain.model.User;
import jose.learning_api_spring.domain.model.Wallet;

import java.util.List;
import java.util.stream.Collectors;
import static java.util.Optional.ofNullable;
import java.util.Collections;

public record UserDto(
        Long id,
        String name,
        String email,
        String password,
        List<WalletDto> wallets
) {
    public UserDto(User model) {
        this(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getPassword(),
                ofNullable(model.getWallets())
                        .map(wallets -> wallets.stream()
                                .map(WalletDto::new)
                                .collect(Collectors.toList()))
                        .orElse(Collections.<WalletDto>emptyList())
        );
    }

    public User toModel() {
        User model = new User();
        model.setId(this.id);
        model.setName(this.name);
        model.setEmail(this.email);
        model.setPassword(this.password);

        List<Wallet> wallets = ofNullable(this.wallets)
                .map(walletDtos -> walletDtos.stream()
                        .map(WalletDto::toModel)
                        .peek(wallet -> wallet.setUser(model)) // Vincula o User
                        .collect(Collectors.toList()))
                .orElse(Collections.<Wallet>emptyList());

        model.setWallets(wallets);
        return model;
    }
}