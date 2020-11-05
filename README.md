# LongMap
Custom map realization 
## Structure
Inside map used dynamically expandable array, assigned class (Node), witch has "key, "value" and "next"
fields. The cell of the array is calculated into which the object will be placed by key. If the 
cell is free, the element is placed there. If busy, then a link to a new object is placed in the
 "next" field.