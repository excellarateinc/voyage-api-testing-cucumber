 # voyage-api-testing-cucumber
Voyage REST API integration testing framework based on Cucumber and Java Spring Boot


 ### voyage api project structure

 #### Start new project
 #####1. Folder structure

<pre>
+---.idea
+---src
|   +---main
|   |   +---java
|   |   |   \---com.lssinc.voyage.api.cucumber
|   |   |   |   |---com.lssinc.voyage.api.cucumber.controller
|   |   |   |   |   \---VoyageApiTestingController.java
|   |   |   |   |---com.lssinc.voyage.api.cucumber.util
|   |   |   |   |   \---Utils.java
|   |   |   |   |   \---VoyageConstants.java
|   |   |   |   |---com.lssinc.voyage.api.cucumber.service
|   |   |   |   |   \---CucumberRunnerService.java
|   |   |   |   \---CucumberRunner.java
|   |   |   |   \---package-info.java
|   |   |   |   \--VoyageApiTestingCucumberApplication.java
|   |   |   |   +---strpdef
|   |   |   |   |   \--VoyageApplicationAuthenticationStepdefs.step
|   |   +---resources
|   |   |   \--application.yml
|   +---test
|   |   +---java
|   |   |   +---com.lssinc.voyage.api.cucumber.test
|   |   |   |   \--VoyageApiTestingCucumberApplicationTests.java
|   |   +---resources
|   |       +---features
|   |   |   |   |---VoyageAuthenticationToken.feature
</pre>

#####2. Install dependencies
Now we can get the project dependencies installed:

Run the gradle build

    ./gradlew build

Observer the dependent jars downloaded


Run the gradle build

    ./gradlew bootRun

Observer the server startup

from the browser Run this:

    http://localhost:8083/api/v1/automation/run

You will get detailed integration test case execution report, also find 
cucumber test reports in directory `main/resources/static`.


Cucumber Style Guide
====================

This is a general cucumber style guide.

Gherkin
-------

Cucumber executes your .feature files, and those files contain
executable specifications written in a language called Gherkin.

Gherkin is plain-text English (or one of 60+ other languages) with a
little extra structure. Gherkin is designed to be easy to learn by
non-programmers, yet structured enough to allow concise description of
examples to illustrate business rules in most real-world domains.

Here is a sample Gherkin document:
<pre>
Feature: Refund item

Scenario: Jeff returns a faulty microwave

Given Jeff has bought a microwave for $100

And he has a receipt

When he returns the microwave

Then Jeff should be refunded $100
</pre>

In Gherkin, each line that isn't blank has to start with a Gherkin
*keyword*, followed by any text you like. The main keywords are:

-   [Feature](#_Feature)

-   [Scenario](#_Scenario)

-   [Given](#_Given), [When](#_When), [Then](#_Then), [And](#and-but),
    [But](#and-but) (Steps)

-   [Background](#_Background)

-   [Scenario Outline](#_Scenario_Outline)

-   [Examples](#_Examples)

There are a few extra keywords as well:

-   """ (Doc Strings)

-   | (Data Tables)

-   @ (Tags)

-   \# (Comments)

### <span id="_Feature" class="anchor"><span id="_Toc500942734" class="anchor"></span></span>Feature

A .feature file is supposed to describe a single feature of the system,
or a particular aspect of a feature. It's just a way to provide a
high-level description of a software feature, and to group related
scenarios.

A feature has three basic elements---the Feature: keyword, a *name* (on
the same line) and an optional (but highly recommended) *description*
that can span multiple lines.

Cucumber does not care about the name or the description---the purpose
is simply to provide a place where you can document important aspects of
the feature, such as a brief explanation and a list of business rules
(general acceptance criteria).

Here is an example:
<pre>
Feature: Refund item

Sales assistants should be able to refund customers' purchases.

This is required by the law, and is also essential in order to

keep customers happy.

Rules:

- Customer must present proof of purchase

- Purchase must be less than 30 days ago
</pre>

Some parts of Gherkin documents do not have to start with a keyword.

On the lines following a Feature, Scenario, Scenario Outline or Examples
you can write anything you like, as long as no line starts with a key a
keyword.

### <span id="scenario" class="anchor"><span id="_Scenario" class="anchor"><span id="_Toc500942735" class="anchor"></span></span></span>Scenario

A scenario is a *concrete example* that *illustrates* a business rule.
It consists of a list of
[steps](https://cucumber.io/docs/reference#steps).

You can have as many steps as you like, but we recommend you keep the
number at 3-5 per scenario. If they become longer than that they lose
their expressive power as specification and documentation.

In addition to being a specification and documentation, a scenario is
also a *test*. As a whole, your scenarios are an *executable
specification* of the system.

Scenarios follow the same pattern:

-   Describe an initial context

-   Describe an event

-   Describe an expected outcome

This is done with steps.

### <span id="steps" class="anchor"><span id="_Toc500942736" class="anchor"></span></span>Steps

A step typically starts with Given, When or Then. If there are multiple
Given or When steps underneath each other, you can use And or But.
Cucumber does not differentiate between the keywords, but choosing the
right one is important for the readability of the scenario as a whole.

#### <span id="given" class="anchor"><span id="_Given" class="anchor"></span></span>Given

Given steps are used to describe the initial context of the system---the
*scene* of the scenario. It is typically something that happened in the
*past*.

When Cucumber executes a Given step it will configure the system to be
in a well-defined state, such as creating and configuring objects or
adding data to the test database.

It's ok to have several Given steps (just use And or But for number 2
and upwards to make it more readable).

#### <span id="when" class="anchor"><span id="_When" class="anchor"></span></span>When

When steps are used to describe an event, or an *action*. This can be a
person interacting with the system, or it can be an event triggered by
another system.

It's strongly recommended you only have a single When step per scenario.
If you feel compelled to add more it's usually a sign that you should
split the scenario up in multiple scenarios.

#### <span id="then" class="anchor"><span id="_Then" class="anchor"></span></span>Then

Then steps are used to describe an *expected* outcome, or result.

The [step
definition](https://cucumber.io/docs/reference#step-definitions) of a
Then step should use an *assertion* to compare the *actual* outcome
(what the system actually does) to the *expected* outcome (what the step
says the system is supposed to do).

#### And, But

If you have several Givens, Whens, or Thens, you *could* write:
<pre>
Scenario: Multiple Givens

Given one thing

Given another thing

Given yet another thing

When I open my eyes

Then I see something

Then I don't see something else
</pre>

Or, you could make it read more fluidly by writing:

<pre>
Scenario: Multiple Givens

Given one thing

And another thing

And yet another thing

When I open my eyes

Then I see something

But I don't see something else
</pre

If you find Given, When, Then, And and But too verbose, you can also use
an additional keyword to start a step: \*(**an asterisk**). For example:

<pre>
Scenario: Attempt withdrawal using stolen card

\* I have $100 in my account

\* my card is invalid

\* I request $50

\* my card should not be returned

\* I should be told to contact the bank
</pre>

### <span id="_Background" class="anchor"><span id="_Toc500942737" class="anchor"></span></span>Background

Occasionally you'll find yourself repeating the same Given steps in all
of the scenarios in a feature file. Since it is repeated in every
scenario it is an indication that those steps are not *essential* to
describe the scenarios, they are *incidental details*.

You can literally move such Given steps to the background by grouping
them under a Background section before the first scenario:

<pre>
Background:

Given a $100 microwave was sold on 2015-11-03

And today is 2015-11-18
</pre>

### <span id="scenario-outline" class="anchor"><span id="_Scenario_Outline" class="anchor"><span id="_Toc500942738" class="anchor"></span></span></span>Scenario Outline

When you have a complex business rule with severable variable inputs or
outputs you might end up creating several scenarios that only differ by
their values.

Let's take an example from [feed planning for cattle and
sheep](http://www.nutrientmanagement.org/assets/12028):

<pre>
Scenario: feeding a small suckler cow

Given the cow weighs 450 kg

When we calculate the feeding requirements

Then the energy should be 26500 MJ

And the protein should be 215 kg

</pre>

<pre>
Scenario: feeding a medium suckler cow

Given the cow weighs 500 kg

When we calculate the feeding requirements

Then the energy should be 29500 MJ

And the protein should be 245 kg

</pre>

\# There are 2 more examples - I'm already bored

If there are many examples, this becomes tedious. We can simplify it
with a Scenario Outline:

<pre>
Scenario Outline: feeding a suckler cow

Given the cow weighs &lt;weight&gt; kg

When we calculate the feeding requirements

Then the energy should be &lt;energy&gt; MJ

And the protein should be &lt;protein&gt; kg

Examples:

| weight | energy | protein |

| 450 | 26500 | 215 |

| 500 | 29500 | 245 |

| 575 | 31500 | 255 |

| 600 | 37000 | 305 |

This is much easier to read.
</pre>

Variables in the Scenario Outline steps are marked up with &lt; and
&gt;.

#### <span id="examples" class="anchor"><span id="_Examples" class="anchor"></span></span>Examples

A Scenario Outline section is always followed by one or more Examples
sections, which are a container for a table.

The table must have a header row corresponding to the variables in the
Scenario Outline steps.

Each of the rows below will create a new Scenario, filling in the
variable values.

#### Scenario Outlines and UI

Automating Scenario Outlines using UI automation such as Selenium
WebDriver is considered a bad practice.

The only good reason to use Scenario Outlines is to validate the
implementation of business rule that behaves differently based on
several input parameters.

Validating a business rule through a UI is slow, and when there is a
failure it is difficult to pinpoint where the error is.

The automation code for Scenario Outlines should communicate directly
with the business rule implementation, going through as few layers as
possible. This is fast, and errors become easy to diagnose fix.

### <span id="step-arguments" class="anchor"><span id="_Toc500942739" class="anchor"></span></span>Step Arguments

In some cases you might want to pass a larger chunk of text or a table
of data to a step---something that doesn't fit on a single line.

For this purpose Gherkin has [Doc
Strings](https://cucumber.io/docs/reference#doc-strings) and [Data
Tables](https://cucumber.io/docs/reference#data-tables).

#### Doc Strings

Doc Strings are handy for passing a larger piece of text to a step
definition. The syntax is inspired from Python's
[Docstring](http://www.python.org/dev/peps/pep-0257/) syntax.

The text should be offset by delimiters consisting of three double-quote
marks on lines of their own:

<pre>
Given a blog post named "Random" with Markdown body

"""

Some Title, Eh?

===============

Here is the first paragraph of my blog post. Lorem ipsum dolor sit amet,

consectetur adipiscing elit.

"""
</pre>

In your [Step
Definition](https://cucumber.io/docs/reference#step-definitions),
there’s no need to find this text and match it in your pattern. It will
automatically be passed as the last parameter in the step definition.

Indentation of the opening """ is unimportant, although common practice
is two spaces in from the enclosing step. The indentation inside the
triple quotes, however, *is* significant. Each line of the Doc String
will be de-indented according to the opening """. Indentation beyond the
column of the opening """ will therefore be preserved.

#### Data Tables

Data Tables are handy for passing a list of values to a step definition:

<pre>
Given the following users exist:

| name | email | twitter |

| Aslak | aslak@cucumber.io | @aslak\_hellesoy |

| Julien | julien@cucumber.io | @jbpros |

| Matt | matt@cucumber.io | @mattwynne |

</pre>

Just like [Doc Strings](https://cucumber.io/docs/reference#doc-strings),
Data Tables will be passed to the [Step
Definition](https://cucumber.io/docs/reference#step-definitions) as the
last argument.

The type of this argument will be DataTable. See the API docs for more
details about how to access the rows and cells.

### <span id="tags" class="anchor"><span id="_Toc500942740" class="anchor"></span></span>Tags

Tags are a way to group Scenarios. They are @-prefixed strings and you
can place as many tags as you like above Feature, Scenario, Scenario
Outline or Examples keywords. Space character are invalid in tags and
may separate them.

Tags are inherited from parent elements. For example, if you place a tag
above a Feature, all scenarios in that feature will get that tag.

Similarly, if you place a tag above a Scenario Outline or Examples
keyword, all scenarios derived from examples rows will inherit the tags.

You can tell Cucumber to only run scenarios with certain tags, or to
exclude scenarios with certain tags.

Cucumber can perform different operations before and after each scenario
based on what tags are present on a scenario.

See [tagged hooks](https://cucumber.io/docs/reference#tagged-hooks) for
more details.

### <span id="comments" class="anchor"><span id="_Toc500942741" class="anchor"></span></span>Comments

Gherkin provides lots of places to document your features and scenarios.
The preferred place is
[descriptions](https://cucumber.io/docs/reference#descriptions).
Choosing good names is also useful.

If none of these places suit you, you can start a line with a \# to tell
Cucumber that the remainder of the line is a comment, and shouldn't be
executed.

<span id="step-definitions" class="anchor"><span id="_Toc500942742" class="anchor"></span></span>Step Definitions
-----------------------------------------------------------------------------------------------------------------

Cucumber doesn't know how to execute your scenarios out-of-the-box. It
needs *Step Definitions* to translate plain text Gherkin steps into
actions that will interact with the system.

When Cucumber executes a
[Step](https://cucumber.io/docs/reference#steps) in a
[Scenario](https://cucumber.io/#scenario) it will look for a matching
*Step Definition* to execute.

A Step Definition is a small piece of *code* with a *pattern* attached
to it. The pattern is used to link the step definition to all the
matching [Steps](https://cucumber.io/docs/reference#steps), and the
*code* is what Cucumber will execute when it sees a Gherkin Step.

To understand how Step Definitions work, consider the following
Scenario:
<pre>
Scenario: Some cukes

Given I have 48 cukes in my belly
</pre>

The I have 48 cukes in my belly part of the step (the text following the
Given keyword) will match the Step Definition below:

When Cucumber matches a Step against a pattern in a Step Definition, it
passes the value of all the capture groups to the Step Definition's
arguments.

Capture groups are strings (even when they match digits like \\d+). For
statically typed languages, Cucumber will automatically transform those
strings into the appropriate type. For dynamically typed languages, no
transformation happens by default, as there is no type information.

Cucumber does not differentiate between the five step keywords Given,
When, Then, And and But.

Reference: <https://cucumber.io/docs/reference>

![alt tag](cucumberReportForReadmeDoc.png, "")

