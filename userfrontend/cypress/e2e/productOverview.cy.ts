describe('template spec', () => {
  it('more products', () => {
    cy.visit('http://localhost:5173/category/CPU/1')
    cy.get('#showMore').click()
    cy.get('#productCard10').should("exist")


  })
})