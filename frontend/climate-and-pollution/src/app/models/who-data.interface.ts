export interface WhoData {
  Id: string
  IndicatorCode: string
  SpatialDimType: string
  SpatialDim: string
  ParentLocationCode: string
  TimeDimType: string
  ParentLocation: string
  TimeDim: number | null
  Dim1: string
  Dim2: string
  Value: string
  NumericValue: number | null
  Low: number | null
  High: number | null
}
