describe('template spec', () => {
  it('more products', () => {
    cy.visit('http://localhost:5173/category/CPU/1')
    cy.get('#showMore').click()
    cy.get('#productCard10').should("exist")


  })
  it('filters products by name', () => {
    cy.visit('http://localhost:5173/category/CPU/1');

    // Filter by name using the search input
    const searchName = 'Ryzen'; // Replace with an actual product name to test
    cy.get('#nameFilter').type(searchName);

    // Check that only products matching the name are visible
    cy.get('#product-cards > a').each(($el) => {
      cy.wrap($el).should('be.visible');
      cy.wrap($el).find('h2').should('contain.text', searchName);
    });
  });

  it('filters products by min price', () => {
    cy.visit('http://localhost:5173/category/CPU/1');


    // Set the minimum price to 300
    const minPrice = 300;
    cy.get('#minPrice').clear().type(minPrice.toString());

    // Check that all visible products have a price >= 300
    cy.get('#product-cards > a').each((el) => {
      cy.wrap(el).should('be.visible');
      cy.wrap(el).find('p.text-xl').then((price) => {
        const priceText = price.text().replace('€', '').trim();
        const priceValue = parseFloat(priceText);
        expect(priceValue).to.be.gte(minPrice); // Assert price is >= 300
      });
    });
  });
})