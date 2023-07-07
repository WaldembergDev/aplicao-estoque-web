package org.github.waldemberg.estoqueapp.service;

import org.github.waldemberg.estoqueapp.model.mail.EmailModel;

public interface EmailService {
    boolean enviarEmail(EmailModel email);
}
