package com.igrowker.nativo.services;

import com.igrowker.nativo.dtos.donation.RequestDonationConfirmationDto;
import com.igrowker.nativo.dtos.donation.RequestDonationDto;
import com.igrowker.nativo.dtos.donation.ResponseDonationConfirmationDto;
import com.igrowker.nativo.dtos.donation.ResponseDonationDto;

public interface DonationService {

    ResponseDonationDto createDonation(RequestDonationDto requestDonationDto);

    ResponseDonationConfirmationDto confirmationDonation(RequestDonationConfirmationDto requestDonationConfirmationDto);

}
