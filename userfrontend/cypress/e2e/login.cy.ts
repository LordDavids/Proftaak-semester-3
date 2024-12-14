describe('template spec', () => {
  it('visits the login page', () => {
    cy.visit('http://localhost:5173')
    cy.get('#login').click()

    cy.url().should('include', '/login')

    cy.get('#email').type('david@localhost.nl')

    cy.get('#password').type('123456')

    cy.get('#submit').click()

    cy.url().should('eq', 'http://localhost:5173/')

    cy.get('#logout').click()
    cy.url().should('eq', 'http://localhost:5173/login')

  })

  it('logout with wrong email', () => {
    cy.visit('http://localhost:5173')
    cy.get('#login').click()

    cy.url().should('include', '/login')

    cy.get('#email').type('notdavid@localhost.nl')

    cy.get('#password').type('123456')

    cy.get('#submit').click()

    cy.get("#errorBox").should('contain', 'invalid credentials')
  });

  it('logout with wrong password', () => {
    cy.visit('http://localhost:5173')
    cy.get('#login').click()

    cy.url().should('include', '/login')

    cy.get('#email').type('david@localhost.nl')

    cy.get('#password').type('not123456')

    cy.get('#submit').click()

    cy.get("#errorBox").should('contain', 'invalid credentials')
  });
})