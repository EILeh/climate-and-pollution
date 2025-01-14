export interface WhoCountries {
  value: Country[]
}
export interface Country {
  Code: string
  Title: string
  ParentDimension: string
  Dimension: string
  ParentCode: string
  ParentTitle: string
}
