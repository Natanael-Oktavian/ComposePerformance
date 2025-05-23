# ComposePerformance

Jetpack Compose Performance Tuning
- derivedStateOf -> To trigger recomposition only when then input actually changed
- rememberUpdatedState -> Capture latest value for coroutine, so coroutine doesnt restart
- LaunchedEffect -> Run coroutine in response to changes in keys

notice that derivedStateOf avoid recomposition unless the derived value actually changes

so in my sample that part of the UI that use isMultiplierOfFive as a derivedStateOf only recompose when value changes (in second 4 to 5 and in second 5 to 6 as the value changes from false to true and true to false)
