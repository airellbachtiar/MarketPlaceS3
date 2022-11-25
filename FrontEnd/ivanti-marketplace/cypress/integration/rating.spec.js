/// <reference types="cypress" />

// Welcome to Cypress!
//
// This spec file contains a variety of sample tests
// for a todo list app that are designed to demonstrate
// the power of writing tests in Cypress.
//
// To learn more about how Cypress works and
// what makes it such an awesome testing tool,
// please read our getting started guide:
// https://on.cypress.io/introduction-to-cypress

describe('rating tests', () => {
  /* ==== Test Created with Cypress Studio ==== */
  it('Add a rating test', {  defaultCommandTimeout: 120000
  } , function() {
    /* ==== Generated with Cypress Studio ==== */
    cy.visit('localhost:3000/login');
    cy.get('[data-cy="login-email"]').clear();
    cy.get('[data-cy="login-email"]').type('b.bandell@fontys.nl');
    cy.get('[data-cy="login-password"]').clear();
    cy.get('[data-cy="login-password"]').type('12345678');
    cy.get('[data-cy="login-submit"]').click();
    cy.get('.navbar > .menu-bars > svg').click();
    cy.get(':nth-child(3) > a > span').click();
    cy.get(':nth-child(3) > .package-card > .package-card-container > .package-card-title-container > foreignobject > .package-card-title').click();
    cy.wait(5000)
    cy.get('button').click();
    cy.get('textarea').type('Test rating');
    cy.get('[style="display: flex; justify-content: center; column-gap: 10%;"] > button').click();
    cy.get('textarea').should('have.value', 'Test rating');
    cy.get('[style="width: 45%; background-color: red; padding: 5px; border-radius: 8px; color: white; border: none; cursor: pointer;"]').should('have.text', 'Delete review');
    /* ==== End Cypress Studio ==== */
  });

  /* ==== Test Created with Cypress Studio ==== */
  it('Edit rating test', {  defaultCommandTimeout: 120000
  }, function() {
    /* ==== Generated with Cypress Studio ==== */
    cy.visit('http://localhost:3000/login');
    cy.get('[data-cy="login-email"]').clear();
    cy.get('[data-cy="login-email"]').type('b.bandell@fontys.nl');
    cy.get('[data-cy="login-password"]').clear();
    cy.get('[data-cy="login-password"]').type('12345678');
    cy.get('[data-cy="login-submit"]').click();
    cy.get('.navbar > .menu-bars > svg').click();
    cy.get(':nth-child(3) > a > span').click();
    cy.get(':nth-child(3) > .package-card > .package-card-container > .package-card-title-container > foreignobject > .package-card-title').click();
    cy.wait(5000)
    cy.get('textarea').type(' 2');
    cy.get('[style="width: 45%; background-color: rgb(103, 6, 120); padding: 5px; border-radius: 8px; color: white; border: none; cursor: pointer;"]').click();
    cy.get('textarea').should('have.value', 'Test rating 2');
    /* ==== End Cypress Studio ==== */
  });

  /* ==== Test Created with Cypress Studio ==== */
  it('Delete rating test', {  defaultCommandTimeout: 120000
  }, function() {
    /* ==== Generated with Cypress Studio ==== */
    cy.visit('http://localhost:3000/login');
    cy.get('[data-cy="login-email"]').clear();
    cy.get('[data-cy="login-email"]').type('b.bandell@fontys.nl');
    cy.get('[data-cy="login-password"]').clear();
    cy.get('[data-cy="login-password"]').type('12345678');
    cy.get('[data-cy="login-submit"]').click();
    cy.get('.navbar > .menu-bars > svg > path').click();
    cy.get(':nth-child(3) > a > span').click();
    cy.get(':nth-child(3) > .package-card > .package-card-container > .package-card-title-container > foreignobject > .package-card-title').click();
    cy.wait(5000);
    cy.get('[style="width: 45%; background-color: red; padding: 5px; border-radius: 8px; color: white; border: none; cursor: pointer;"]').click();
    cy.get('[style="margin-left: 2%; background-color: rgba(211, 211, 211, 0.8); border-radius: 10px; padding: 15px; min-width: 45%;"]').click();
    cy.get('[style="margin-left: 2%; background-color: rgba(211, 211, 211, 0.8); border-radius: 10px; padding: 15px; min-width: 45%;"] > :nth-child(1)').click();
    cy.get(':nth-child(1) > button').should('have.text', 'Leave a rating');
    /* ==== End Cypress Studio ==== */
  });
})


