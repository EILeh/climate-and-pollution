export default interface Settings {
  chart?: ChartSettings
  map?: MapSettings
}

export interface ChartSettings {
  country?: string
  yearRange?: YearRange
  filters?: SettingsFilters
}

export interface MapSettings {
  city?: string
  filters?: SettingsFilters
}

export interface YearRange {
  low: number
  high: number
}

export interface SettingsFilters {
  air15?: boolean
  air43?: boolean
  air41?: boolean
  cco2?: boolean
  air35?: boolean
  air11?: boolean
  sdgpm25?: boolean
  sdgairboda?: boolean
  poppollutingfuels?: boolean
  mortality?: boolean
  xTemp?: boolean
  drought?: boolean
  co?: boolean
  no?: boolean
  no2?: boolean
  o3?: boolean
  so2?: boolean
  pm2_5?: boolean
  pm10?: boolean
  nh3?: boolean
}
