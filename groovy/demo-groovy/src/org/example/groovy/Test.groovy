package org.example.groovy

import groovy.transform.BaseScript
import groovy.transform.TypeChecked

@BaseScript DslDelegate _

class EmailSpec {
    void from(String from) { println "From: $from" }
    void to(String... to) { println "To: $to" }
    void subject(String subject) { println "Subject: $subject" }
}

def email(@DelegatesTo(EmailSpec) Closure cl) {
    def email = new EmailSpec()
    def code = cl.rehydrate(email, this, this)
    code.resolveStrategy = Closure.DELEGATE_ONLY
    code()
}

@TypeChecked
void sendEmail() {
    email {
        from 'dsl-guru@mycompany.com'
        to 'john.doe@waitaminute.com'
        subject 'The pope has resigned!'
    }
}
