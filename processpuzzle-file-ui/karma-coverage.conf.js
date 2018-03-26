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
            require('karma-remap-coverage'),
            require('karma-coverage-istanbul-reporter')
        ],
        preprocessors: {
            './src/**/!(*spec).js': 'coverage'
        },

        reporters: ['progress', 'kjhtml', 'coverage', 'remap-coverage'],

        coverageReporter: {
            type: 'in-memory',
            check: {
                global: {
                    statements: 60,
                    branches: 60,
                    functions: 60,
                    lines: 60
                },
                each: {
                    statements: 60,
                    branches: 60,
                    functions: 60,
                    lines: 60
                }
            }
        },

        remapCoverageReporter: {
            'text-summary': null,
            html: './target/coverage/html',
            cobertura: './target/coverage/coverage.xml'
        }
    })
}
