# JSON64BasedDiff
Rest service that compares two given 64based jsons and returns an Insight.

Developed using Java, spring boot, jpa, h2 and maven in Eclise neon IDE.


## What it does?
Given two jsons objects with the data of a field being a 64based content, *JSON64BasedDiff* compares them and calcutates an *Insight*. 

Insight contains the details related to the diffs, the offset and size of the difference.
The calculation rules are: 

1. If data of both json objects are equal then return that:

```
	Example:

	Left: 	{"encodedData":"bGVmdERhdGE="}
	Right:	{"encodedData":"bGVmdERhdGE="}

  The Data being compared is "bGVmdERhdGE=" and "bGVmdERhdGE=".
  
	Insight returned is:
	{
		"areEqual": true,
		"areEqualSize": true,
		"diffs": []
	}
```

2. If size of the data of both json objects are different then the Insight should say that they are not equal and that they are not of the same size:

```
	Example:

  Left: 	{"encodedData":"bGVmd="}
	Right:	{"encodedData":"bGVmdERhdGE="}
  
  The Data being compared is "bGVmd=" and "bGVmdERhdGE=".

	Insight returned is:
	{
		"areEqual": false,
		"areEqualSize": false,
		"diffs": []
	}
```

3. If size of the data of both json objects are the same but they are different, a Insight is provided with the offsets and size of the diffs:

```
	Example:

  Left: 	{"encodedData":"bGVmdERhdGE="}
	Right:	{"encodedData":"bGVEdmRdhGE="}
  
  The Data being compared is "bGVmdERhdGE=" and "bGVEdmRdhGE=".

	Insight returned is:
	{
		"areEqual": false,
		"areEqualSize": true,
		"diffs": [
			{
				"offset": 4,   
				"lenght": 3
			},
			{
				"offset": 8,
				"length": 2
			}
		]
    
    The content of position 4 for both elements being compared are different. For the left it is "m" as for the right the value is "E" therefore the offset is 4, size 3 because the content of position 4, 5 and 6 in both data are different.
    
    Same process is applied for the second offset.
	}
```

## Assumptions

The main assumption is that the service accepts *MediaType.APPLICATION_JSON* and the content of the binary data has to be encoded and wrapped in a regular json format request.

```
  Exemple: 
  {
    "encodedData":"base64 data"
  }
```



