Common ideas about the structure (aka. request-response, folders etc) for web/middleware app
=======

There is no references to any particular web framework though.

- All Services returns Try[Response] so all runtime exceptions will be caught/wrapped

- use case classes for Request / Response object (with no logic whatsoever in it, aka. DTO).
    - Apply method (companion object for the case classes) for the creating / transformation logic
   for those Request/Response
    - all request-responses are immutable (no var but val)