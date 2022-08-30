# build.gradle code completion contributor
Plugin and example project in examples dir to showcase the difference in code completion for a property in build.gradle


## How to reproduce the issue
1. Clone the project
2. Open it in IntelliJ IDEA and let it index
3. Run 'Run Plugin' task to launch the sandbox instance
4. In the newly opened sandbox instance, click 'Open' and select the build.gradle file at <this-project>/examples/project-with-spec-property/
5. Let the project index
6. Open build.gradle file to test code completion
7. Type 'spec.' and press Ctrl+Space (depending upon your keymap) to see code completion options
8. It will show foo, bar, baz (coming from the plugin defined in this project)
9. Uncomment the line `apply plugin: GreetingPlugin`
10. Click 'Reload Gradle project' button in the Gradle toolbar
11. Try the code-completion again with spec.^
12. It will show the methods for the type Map (i.e. the type of spec property defined in GreetingPlugin)

## Works fine in file-based project files
Note: Choose the project directory and do NOT select the build.gradle to open the project
1. Open the sample project at <this-project>/examples/project-with-idea-plugin
2. Repeat the steps from the previous section and you would see code-completion works in both scenarios.


<!-- Plugin description -->
Sample Plugin to demonstrate custom code completion in build.gradle
[gh:template]: https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template
<!-- Plugin description end -->

