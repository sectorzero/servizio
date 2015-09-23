Dropwizard / Guice Integration
--------------------------------
Content in this package copied from https://github.com/jclawson/dropwizardry/tree/master/dropwizardry-guice

Purpose :
* The popular integration for guice with dropwizard is dropwizard-guice. Unfortunately this does not do the
right thing as the Guice injector is run much before all the framework objects are constructed and available
for other objects to be constructed by the DI. The dropwizardary-guice does the right thing. There were no
examples of usage and some work was done to get this to get it integrated. Since there was no easy way to
bundle this the guice part, it has been copied over. Also might make some changes.

Changes :
* None yet
