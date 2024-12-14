describe('template spec', () => {
  it('register', () => {
    cy.visit('http://localhost:5173/register')

    cy.get('#firstName').type('user')
    cy.get('#lastName').type('user')
    cy.get('#email').type('user@localhost.nl')
    cy.get('#password').type('user123')
    cy.get('#confirmPassword').type('user123')
    cy.get('#phoneNumber').type('0655555555')

    cy.get('#submit').click()

    cy.url().should('eq', 'http://localhost:5173/')

    cy.get('#logout').click()
  })

  it('register no username', () => {
    cy.visit('http://localhost:5173/register')

    cy.get('#lastName').type('user')
    cy.get('#email').type('user@localhost.nl')
    cy.get('#password').type('user123')
    cy.get('#confirmPassword').type('user123')
    cy.get('#phoneNumber').type('0655555555')

    cy.get('#submit').click()

    cy.url().should('eq', 'http://localhost:5173/register')

  })

  it('register user already exists with this email', () => {
    cy.visit('http://localhost:5173/register')

    cy.get('#firstName').type('user')
    cy.get('#lastName').type('user')
    cy.get('#email').type('david@localhost.nl')
    cy.get('#password').type('123456')
    cy.get('#confirmPassword').type('123456')
    cy.get('#phoneNumber').type('0655555555')

    cy.get('#submit').click()

    cy.url().should('eq', 'http://localhost:5173/register')

    cy.get("#errorBox").should('contain', 'User with this email already exists')

  })
})