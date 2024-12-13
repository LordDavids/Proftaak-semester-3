import {defineConfig} from "cypress";
import {runQuery} from "./cypress/scripts/resetScript";

export default defineConfig({
    e2e: {
        setupNodeEvents(on, config) {
            on('task', {
                resetDatabase() {
                    return new Promise((resolve) => runQuery()
                        .then(() => {
                            resolve(null);
                        })
                    );
                },
            })
        },

        supportFile: "cypress/support/e2e.ts",
        baseUrl: 'http://localhost:5173/'
    },

    component: {
        devServer: {
            framework: "vue",
            bundler: "vite",
        },
    },
});
