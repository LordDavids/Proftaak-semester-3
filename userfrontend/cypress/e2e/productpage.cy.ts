describe('template spec', () => {
  it('productpage', () => {
    cy.visit('http://localhost:5173/product/1')
    cy.get('#productName').should('have.text', 'Ryzen 5600X')
  })
})