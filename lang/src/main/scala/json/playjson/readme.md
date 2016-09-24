1. Say annotations "NO" 
2. Say filterable serialization "NO" 
3. Think, maybe you don't need to create case classes at all - just use JsonValue
   (
     basically we want objects for:
      1. better ide support - to be able to access fields by dot (.)
      2. to be able to use equals and other methods without converting json to object all the time
   )
// --

I do not see good reason to use annotations for model classes that are intended
to be serialized to json. Like usually we do for java + jakson:

```
public class Name {
  @JsonProperty("firstName")
  public String _first_name;
}
```

That all makes overcomplicated at once.

Much nicer to use case classes dedicated to be 'transfer object' if you will with all 
fields ready to be converted into json. Si like this:

```
case class Name(firstName:String)
```


then convert/write it to json:

Json.

```
 Json.toJson(name)
```
 
where 'writer' is defined in companion object and included automatically for toJosn() functon to operate with

```
object Name {
   implicit val nameWriter = Json.writes[Name]
}
```

or even

```
object Name {
 implicit val nameFormat = Json.format[Name]
}
```


If we found ourself to filter fields to be serialized, then just do not do it. 
Just create new case class for that or use JsonValue directly.

About the json request/responses standards:
http://stackoverflow.com/questions/12806386/standard-json-api-response-format
