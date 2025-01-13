describe('template spec', () => {
  it('productpage', () => {
    cy.visit('http://localhost:5173/product/1')
    cy.get('#productName').should('have.text', 'Ryzen 5600X')
    cy.get('#articleNumber').should('have.text', 'EAN: 730143312042')
    cy.get('#price').should('have.text', '€300')
    cy.get('#description').should('have.text', 'This processor features a 3.7 GHz base clock, 6 cores, 12 threads, and comes with the AMD Wraith Stealth Cooler.')

  })
})