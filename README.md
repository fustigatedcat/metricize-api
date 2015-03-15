# Metricize API

This is the basic web interface that the agent will communicate with.  The basic conversation is as follows

Agent will create a new agent if necessary which will return a specific agent key.  Once that has been generated
the agent will load it's configuration.

> If agent type is NONE, then we will continue to poll until the agent is configured, once the agent is configured,
> it will automatically restart after pulling down the appropriate jar.

The agent will then start querying for data and push it's information to the API.  The API then pushes the data
onto a queue which will then parse the collected data.