package com.igrowker.nativo.services.implementation;

import com.igrowker.nativo.dtos.donation.*;
import com.igrowker.nativo.entities.Account;
import com.igrowker.nativo.entities.Donation;
import com.igrowker.nativo.entities.TransactionStatus;
import com.igrowker.nativo.entities.User;
import com.igrowker.nativo.exceptions.GlobalExceptionHandler;
import com.igrowker.nativo.exceptions.InsufficientFundsException;
import com.igrowker.nativo.exceptions.ResourceNotFoundException;
import com.igrowker.nativo.mappers.DonationMapper;
import com.igrowker.nativo.repositories.AccountRepository;
import com.igrowker.nativo.repositories.DonationRepository;
import com.igrowker.nativo.repositories.UserRepository;
import com.igrowker.nativo.services.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDonationDtoTrue createDonationTrue(RequestDonationDto requestDonationDto) {

            // Validando cuenta de donador y beneficiario
            Account accountDonor = accountRepository.findById(requestDonationDto.accountIdDonor()).orElseThrow(() -> new InsufficientFundsException("El id de la cuenta donante no existe"));
            Account accountBeneficiary = accountRepository.findById(requestDonationDto.accountIdBeneficiary()).orElseThrow(() -> new InsufficientFundsException("El id de la cuenta beneficiario no existe"));

            User donor = userRepository.findById(accountDonor.getUserId()).orElseThrow(() -> new InsufficientFundsException("El id del usuario donante no existe"));
            User beneficiary = userRepository.findById(accountBeneficiary.getUserId()).orElseThrow(() -> new InsufficientFundsException("El id del usuario beneficiario no existe"));

            Donation donation =donationRepository.save(donationMapper.requestDtoToDonation(requestDonationDto));

            return new ResponseDonationDtoTrue(
                    donation.getId(),
                    donation.getAmount(),
                    accountDonor.getId(),
                    donor.getName(),
                    donor.getSurname(),
                    accountBeneficiary.getId(),
                    beneficiary.getName(),
                    beneficiary.getSurname(),
                    donation.getCreatedAt(),
                    donation.getStatus().name()
            );
    }

    @Override
    public ResponseDonationDtoFalse createDonationFalse(RequestDonationDto requestDonationDto) {

        // Validando cuenta de donador y beneficiario
        Account accountDonor = accountRepository.findById(requestDonationDto.accountIdDonor()).orElseThrow(() -> new InsufficientFundsException("El id de la cuenta donante no existe"));
        Account accountBeneficiary = accountRepository.findById(requestDonationDto.accountIdBeneficiary()).orElseThrow(() -> new InsufficientFundsException("El id de la cuenta beneficiario no existe"));

        User donor = userRepository.findById(accountDonor.getUserId()).orElseThrow(() -> new InsufficientFundsException("El id del usuario donante no existe"));
        User beneficiary = userRepository.findById(accountBeneficiary.getUserId()).orElseThrow(() -> new InsufficientFundsException("El id del usuario beneficiario no existe"));


        return donationMapper.donationToResponseDtoFalse(donationRepository.save(donationMapper.requestDtoToDonation(requestDonationDto)));

    }

    @Override
    public ResponseDonationConfirmationDto confirmationDonation(RequestDonationConfirmationDto requestDonationConfirmationDto) {


            Donation donation = donationRepository.findById(requestDonationConfirmationDto.id())
                    .orElseThrow(() -> new InsufficientFundsException("El id de la donacion no existe"));

            Donation donation1 = donationMapper.requestConfirmationDtoToDonation(requestDonationConfirmationDto);

            donation1.setAnonymousDonation(donation.getAnonymousDonation());
            donation1.setCreatedAt(donation.getCreatedAt());

            if (donation.getStatus() == TransactionStatus.ACCEPTED){
                // Se agrega el monto al beneficiario


                return  donationMapper.donationToResponseConfirmationDto(donationRepository.save(donation1));
            }else{
                // Se agrega el monto al donante

                return  donationMapper.donationToResponseConfirmationDto(donationRepository.save(donation1));
            }



    }

    @Override
    public List<ResponseDonationRecordBeneficiary> recordDonationDonor(String idAccount) {

        //Validar si la cuenta existe
        Account account =  accountRepository.findById(idAccount).orElseThrow(() -> new InsufficientFundsException("La cuenta no existe"));

        // Obteniedo listado
        //validar si la lista esta vacia, en el caso comunicarlo

        List<Donation> donationList = donationRepository.findAllByAccountIdDonor(account.getId()).orElseThrow(() -> new InsufficientFundsException("No hay donacion que tenga ese id de cuenta"));

        if (donationList.isEmpty()){
            throw  new ResourceNotFoundException("No hay donaciones dadas");
        }else {
            return donationMapper.listDonationToListResponseDonationRecord(donationList);
        }


    }


    @Override
    public List<ResponseDonationRecordBeneficiary> recordDonationBeneficiary(String idAccount) {


        Account account =  accountRepository.findById(idAccount).orElseThrow(() -> new InsufficientFundsException("La cuenta no existe"));

        List<Donation> donationList = donationRepository.findAllByAccountIdBeneficiary(account.getId()).orElseThrow(() -> new InsufficientFundsException("No hay donacion que tenga ese id de cuenta"));

        if (donationList.isEmpty()){
            throw  new ResourceNotFoundException("No hay donaciones recibidas");
        } else {
            return donationMapper.listDonationToListResponseDonationRecord(donationList);
        }


    }

}
