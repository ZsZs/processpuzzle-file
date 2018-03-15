const karmaConf = require('./karma.conf.js');
module.exports = function (config) {
    // Generic Karma Configuration
    karmaConf(config);

    //Extended Configuration for Karma Coverage Reports
    config.set({
        plugins: [
            require('karma-jasmine'),
            require('karma-chrome-launcher'),
            require('karma-jasmine-html-reporter'),
            require('karma-coverage'),
            require('karma-remap-coverage')
        ],
        preprocessors: {
            './src/**/!(*spec).js': 'coverage'
        },

        reporters: ['progress', 'kjhtml', 'coverage', 'remap-coverage'],

        coverageReporter: {
            type: 'in-memory',
            check: {
                global: {
                    statements: 50,
                    branches: 50,
                    functions: 50,
                    lines: 50
                },
                each: {
                    statements: 50,
                    branches: 50,
                    functions: 50,
                    lines: 50
                }
            }
        },

        remapCoverageReporter: {
            'text-summary': null,
            'text-lcov': './target/coverage/lcov.info',
            html: './target/coverage/html',
            cobertura: './target/coverage/coverage.xml'
        }
    })
}
