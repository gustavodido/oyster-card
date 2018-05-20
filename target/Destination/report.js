$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("AcceptanceTests.feature");
formatter.feature({
  "line": 1,
  "name": "Oyster Card Problem",
  "description": "",
  "id": "oyster-card-problem",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 3,
  "name": "User successfully take a set of trips",
  "description": "",
  "id": "oyster-card-problem;user-successfully-take-a-set-of-trips",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 4,
  "name": "the user Gustavo has loaded £30.00 in his card",
  "keyword": "Given "
});
formatter.step({
  "line": 5,
  "name": "he passes through the inward barrier at the Holborn station",
  "keyword": "When "
});
formatter.step({
  "line": 6,
  "name": "he takes a train",
  "keyword": "And "
});
formatter.step({
  "line": 7,
  "name": "he swipes out at the Earl\u0027s Court",
  "keyword": "And "
});
formatter.step({
  "line": 8,
  "name": "he passes through the inward barrier at the Earl\u0027s Court station",
  "keyword": "And "
});
formatter.step({
  "line": 9,
  "name": "he takes a bus",
  "keyword": "And "
});
formatter.step({
  "line": 10,
  "name": "he swipes out at the Chelsea",
  "keyword": "And "
});
formatter.step({
  "line": 11,
  "name": "he passes through the inward barrier at the Earl\u0027s Court station",
  "keyword": "And "
});
formatter.step({
  "line": 12,
  "name": "he takes a train",
  "keyword": "And "
});
formatter.step({
  "line": 13,
  "name": "he swipes out at the Hammersmith",
  "keyword": "And "
});
formatter.step({
  "line": 14,
  "name": "his card balance is £23.72",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "Gustavo",
      "offset": 9
    },
    {
      "val": "30.00",
      "offset": 29
    }
  ],
  "location": "StepDefinitions.theUserGustavoHasLoadedSomAmountInHisCard(String,BigDecimal)"
});
formatter.result({
  "duration": 143913391,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Holborn",
      "offset": 44
    }
  ],
  "location": "StepDefinitions.hePassesThroughTheInwardBarrierAtStation(String)"
});
formatter.result({
  "duration": 79970379,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "train",
      "offset": 11
    }
  ],
  "location": "StepDefinitions.heTakesATrain(String)"
});
formatter.result({
  "duration": 109554,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Earl\u0027s Court",
      "offset": 21
    }
  ],
  "location": "StepDefinitions.heSwipesOutAtTheStation(String)"
});
formatter.result({
  "duration": 10288635,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Earl\u0027s Court",
      "offset": 44
    }
  ],
  "location": "StepDefinitions.hePassesThroughTheInwardBarrierAtStation(String)"
});
formatter.result({
  "duration": 387627,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "bus",
      "offset": 11
    }
  ],
  "location": "StepDefinitions.heTakesATrain(String)"
});
formatter.result({
  "duration": 82552,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Chelsea",
      "offset": 21
    }
  ],
  "location": "StepDefinitions.heSwipesOutAtTheStation(String)"
});
formatter.result({
  "duration": 194598,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Earl\u0027s Court",
      "offset": 44
    }
  ],
  "location": "StepDefinitions.hePassesThroughTheInwardBarrierAtStation(String)"
});
formatter.result({
  "duration": 488737,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "train",
      "offset": 11
    }
  ],
  "location": "StepDefinitions.heTakesATrain(String)"
});
formatter.result({
  "duration": 55719,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Hammersmith",
      "offset": 21
    }
  ],
  "location": "StepDefinitions.heSwipesOutAtTheStation(String)"
});
formatter.result({
  "duration": 178130,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "23.72",
      "offset": 21
    }
  ],
  "location": "StepDefinitions.hisCardBalanceIs(BigDecimal)"
});
formatter.result({
  "duration": 4092046,
  "error_message": "java.lang.AssertionError: \nExpected: is \u003c23.72\u003e\n     but: was \u003c23.7\u003e\n\tat org.hamcrest.MatcherAssert.assertThat(MatcherAssert.java:18)\n\tat org.junit.Assert.assertThat(Assert.java:956)\n\tat org.junit.Assert.assertThat(Assert.java:923)\n\tat acceptance.tests.steps.StepDefinitions.hisCardBalanceIs(StepDefinitions.java:76)\n\tat ✽.Then his card balance is £23.72(AcceptanceTests.feature:14)\n",
  "status": "failed"
});
});