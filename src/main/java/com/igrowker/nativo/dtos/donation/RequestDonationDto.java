package com.igrowker.nativo.dtos.donation;

import com.igrowker.nativo.entities.Account;
import com.igrowker.nativo.entities.User;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record RequestDonationDto(
        @NotNull(message = "El Monto de la donacion es nulo.")
        BigDecimal amount,

        @NotNull(message = "La cuenta del donante de la donacion es nulo.")
        String accountIdDonor,

        @NotNull(message = "La cuenta del beneficiario de la donacion es nulo.")
        String accountIdBeneficiary,

        @NotNull(message = "El estado de la opcion de anonimato es nulo")
        Boolean anonymousDonation
) {}
