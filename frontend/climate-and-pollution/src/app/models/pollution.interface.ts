import { Coordinates } from './coordinates.interface'

export interface Pollution {
  coord: Coordinates
  list: {
    dt: number
    main: {
      aqi: number
    }
    components: {
      co: number
      no: number
      no2: number
      o3: number
      so2: number
      pm2_5: number
      pm10: number
      nh3: number
    }
  }[]
  city: string
  country: string
}
