import { Coordinates } from './coordinates.interface'

export interface YearlyAirPollutionData {
    cityName: string;
    lat: number;
    lon: number;
    year: number;
    averagedComponents: {
      co: number;
      no: number;
      no2: number;
      o3: number;
      so2: number;
      pm2_5: number;
      pm10: number;
      nh3: number;
    };
  }