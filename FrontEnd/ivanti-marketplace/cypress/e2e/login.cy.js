import React from "react";
<reference types="cypress" />

describe("Login", () => {
    it("should not login, wrong input", () => {
        cy.visit('http://localhost:3000/');
        cy.get('[data-cy="profile-menu"]').click();
        cy.get('[data-cy="profile-menu-login"]').click();
        cy.get('[data-cy="login-email"]').type('asdasd@asd.sd');
        cy.get('[data-cy="login-password"]').type('asdasd');
        cy.get('[data-cy="login-submit"]').click();
        cy.get('[data-cy="login-error"]').should("exist");
    });

    it("should not login, empty password", () => {
        cy.visit('http://localhost:3000/');
        cy.get('[data-cy="profile-menu"]').click();
        cy.get('[data-cy="profile-menu-login"]').click();
        cy.get('[data-cy="login-email"]').type('asdasd@asd.sd');
        cy.get('[data-cy="login-submit"]').click();
        cy.get('[data-cy="login-password-error"]').should("exist");
    });

    it("should not login, empty email", () => {
        cy.visit('http://localhost:3000/');
        cy.get('[data-cy="profile-menu"]').click();
        cy.get('[data-cy="profile-menu-login"]').click();
        cy.get('[data-cy="login-submit"]').click();
        cy.get('[data-cy="login-email-error"]').should("exist");
    });

    it("should login and then logout", () => {
        cy.visit('http://localhost:3000/');
        cy.get('[data-cy="profile-menu"]').click();
        cy.get('[data-cy="profile-menu-login"]').click();
        cy.get('[data-cy="login-email"]').type('a.bachtiar@fontys.nl');
        cy.get('[data-cy="login-password"]').type('123');
        cy.get('[data-cy="login-submit"]').click();
        cy.url().should('include', '/');
        cy.get('[data-cy="profile-menu"]').click();
        cy.get('[data-cy="profile-menu-logout"]').click();
        cy.url().should('include', '/');
    });
}
);
