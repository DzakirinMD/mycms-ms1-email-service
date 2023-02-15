package com.mycmsms1emailservice.service;

import com.mycmsms1emailservice.domain.EmailDetails;

public interface EmailService {

    String sendSimpleEmail(EmailDetails emailDetails);
}
