import { WhoData } from './../models/who-data.interface'
import { HttpClient, HttpParams } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { Observable, catchError, map, throwError } from 'rxjs'
import { YearlyAirPollutionData } from '../models/yearly-air-pollutions-data'
import { Pollution } from '../models/pollution.interface'
import { WhoCountries } from '../models/countries.interface'
import { OECDData } from '../models/oecd-data.interface'
import { ChartDataModel } from '../models/chart-data.interface'
import Settings from '../models/settings.interface'

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor(private http: HttpClient) {}

  public getAir15(
    country: string,
    gtYear?: number,
    ltYear?: number
  ): Observable<(ChartDataModel | null)[]> {
    let params = new HttpParams().set('country', country)

    if (gtYear) {
      params = params.set('gtyear', gtYear.toString())
    }

    if (ltYear) {
      params = params.set('ltyear', ltYear.toString())
    }
    return this.http
      .get<WhoData[]>(`/api/air15?`, {
        params,
      })
      .pipe(
        map((response) => this.parseWhoData(response)),
        catchError((err) => {
          console.error('Error fetching air15: ', err)
          return throwError(() => new Error('Failed to fetch air15'))
        })
      )
  }

  public getPollution(
    city: string,
    start?: string,
    end?: string
  ): Observable<Pollution> {
    let params = new HttpParams().set('city', city)

    if (start) {
      params = params.set('start', this.convertToUnix(start))
    }
    if (end) {
      params = params.set('end', this.convertToUnix(end))
    }

    return this.http.get<Pollution>('/api/pollution', { params }).pipe(
      catchError((err) => {
        console.error('Error fetching pollution: ', err)
        return throwError(() => new Error('Failed to fetch pollution'))
      })
    )
  }

  public getYearlyAirPollution(
    city: string,
    start?: string,
    end?: string
  ): Observable<YearlyAirPollutionData[]> {
    let params = new HttpParams().set('city', city)

    if (start) {
      params = params.set(
        'start',
        (new Date(start).getTime() / 1000).toString()
      )
    }
    if (end) {
      params = params.set('end', (new Date(end).getTime() / 1000).toString())
    }

    return this.http
      .get<YearlyAirPollutionData[]>('/api/pollution-history', {
        params,
      })
      .pipe(
        catchError((err) => {
          console.error('Error fetching yearlyAirPollution: ', err)
          return throwError(
            () => new Error('Failed to fetch yearlyAirPollution')
          )
        })
      )
  }

  public getAir43(
    country: string,
    gtYear?: number,
    ltYear?: number
  ): Observable<(ChartDataModel | null)[]> {
    let params = new HttpParams().set('country', country)

    if (gtYear) {
      params = params.set('gtyear', gtYear.toString())
    }

    if (ltYear) {
      params = params.set('ltyear', ltYear.toString())
    }
    return this.http.get<WhoData[]>(`/api/air43?`, { params }).pipe(
      map((response) => this.parseWhoData(response)),
      catchError((err) => {
        console.error('Error fetching air43: ', err)
        return throwError(() => new Error('Failed to fetch air43'))
      })
    )
  }

  public getAir41(
    country: string,
    gtYear?: number,
    ltYear?: number
  ): Observable<(ChartDataModel | null)[]> {
    let params = new HttpParams().set('country', country)

    if (gtYear) {
      params = params.set('gtyear', gtYear.toString())
    }

    if (ltYear) {
      params = params.set('ltyear', ltYear.toString())
    }
    return this.http
      .get<WhoData[]>(`/api/air41`, {
        params,
      })
      .pipe(
        map((response) => this.parseWhoData(response)),
        catchError((err) => {
          console.error('Error fetching air41: ', err)
          return throwError(() => new Error('Failed to fetch air41'))
        })
      )
  }

  public getCCO2(
    country: string,
    gtYear?: number,
    ltYear?: number
  ): Observable<(ChartDataModel | null)[]> {
    let params = new HttpParams().set('country', country)

    if (gtYear) {
      params = params.set('gtyear', gtYear.toString())
    }

    if (ltYear) {
      params = params.set('ltyear', ltYear.toString())
    }
    return this.http
      .get<WhoData[]>(`/api/cco2`, {
        params,
      })
      .pipe(
        map((response) => this.parseWhoData(response)),
        catchError((err) => {
          console.error('Error fetching CCO2: ', err)
          return throwError(() => new Error('Failed to fetch CCO2'))
        })
      )
  }

  public getAir35(
    country: string,
    gtYear?: number,
    ltYear?: number
  ): Observable<(ChartDataModel | null)[]> {
    let params = new HttpParams().set('country', country)

    if (gtYear) {
      params = params.set('gtyear', gtYear.toString())
    }

    if (ltYear) {
      params = params.set('ltyear', ltYear.toString())
    }
    return this.http
      .get<WhoData[]>(`/api/air35`, {
        params,
      })
      .pipe(
        map((response) => this.parseWhoData(response)),
        catchError((err) => {
          console.error('Error fetching air35: ', err)
          return throwError(() => new Error('Failed to fetch air35'))
        })
      )
  }

  public getAir11(
    country: string,
    gtYear?: number,
    ltYear?: number
  ): Observable<(ChartDataModel | null)[]> {
    let params = new HttpParams().set('country', country)

    if (gtYear) {
      params = params.set('gtyear', gtYear.toString())
    }

    if (ltYear) {
      params = params.set('ltyear', ltYear.toString())
    }
    return this.http
      .get<WhoData[]>(`/api/air11`, {
        params,
      })
      .pipe(
        map((response) => this.parseWhoData(response)),
        catchError((err) => {
          console.error('Error fetching air11: ', err)
          return throwError(() => new Error('Failed to fetch air11'))
        })
      )
  }

  public getSDGPM25(
    country: string,
    gtYear?: number,
    ltYear?: number
  ): Observable<(ChartDataModel | null)[]> {
    let params = new HttpParams().set('country', country)

    if (gtYear) {
      params = params.set('gtyear', gtYear.toString())
    }

    if (ltYear) {
      params = params.set('ltyear', ltYear.toString())
    }
    return this.http
      .get<WhoData[]>(`/api/sdgpm25`, {
        params,
      })
      .pipe(
        map((response) => this.parseWhoData(response)),
        catchError((err) => {
          console.error('Error fetching SDGPM25: ', err)
          return throwError(() => new Error('Failed to fetch SDGPM25'))
        })
      )
  }

  public getSDGAirBODA(
    country: string,
    gtYear?: number,
    ltYear?: number
  ): Observable<(ChartDataModel | null)[]> {
    let params = new HttpParams().set('country', country)

    if (gtYear) {
      params = params.set('gtyear', gtYear.toString())
    }

    if (ltYear) {
      params = params.set('ltyear', ltYear.toString())
    }
    return this.http
      .get<WhoData[]>(`/api/sdgairboda`, {
        params,
      })
      .pipe(
        map((response) => this.parseWhoData(response)),
        catchError((err) => {
          console.error('Error fetching SDGAirBODA: ', err)
          return throwError(() => new Error('Failed to fetch SDGAirBODA'))
        })
      )
  }

  public getPopPollutingFuels(
    country: string,
    gtYear?: number,
    ltYear?: number
  ): Observable<(ChartDataModel | null)[]> {
    let params = new HttpParams().set('country', country)

    if (gtYear) {
      params = params.set('gtyear', gtYear.toString())
    }

    if (ltYear) {
      params = params.set('ltyear', ltYear.toString())
    }
    return this.http
      .get<WhoData[]>(`/api/poppollutingfuels`, {
        params,
      })
      .pipe(
        map((response) => this.parseWhoData(response)),
        catchError((err) => {
          console.error('Error fetching popPollutingFuels: ', err)
          return throwError(
            () => new Error('Failed to fetch popPollutingFuels')
          )
        })
      )
  }

  public getMortality(country: string): Observable<(ChartDataModel | null)[]> {
    return this.http.get<OECDData[]>(`/api/mortality?country=${country}`).pipe(
      map((response) => this.parseOecdData(response)),
      catchError((err) => {
        if (err.status === 503) {
          return throwError(() => err)
        } else {
          console.error('Error fetching mortality: ', err)
          return throwError(() => new Error('Failed to fetch mortality'))
        }
      })
    )
  }
  public getXTemp(country: string): Observable<(ChartDataModel | null)[]> {
    return this.http.get<OECDData[]>(`/api/xtemp?country=${country}`).pipe(
      map((response) => this.parseOecdData(response)),
      catchError((err) => {
        if (err.status === 503) {
          return throwError(() => err)
        } else {
          console.error('Error fetching xTemp: ', err)
          return throwError(() => new Error('Failed to fetch xTemp'))
        }
      })
    )
  }
  public getDrought(country: string): Observable<(ChartDataModel | null)[]> {
    return this.http.get<OECDData[]>(`/api/drought?country=${country}`).pipe(
      map((response) => this.parseOecdData(response)),
      catchError((err) => {
        if (err.status === 503) {
          return throwError(() => err)
        } else {
          console.error('Error fetching drought: ', err)
          return throwError(() => new Error('Failed to fetch drought'))
        }
      })
    )
  }

  public getCountries(): Observable<WhoCountries> {
    return this.http.get<WhoCountries>('/assets/DimensionValues.json').pipe(
      catchError((err) => {
        console.error('Error fetching countries: ', err)
        return throwError(() => new Error('Failed to fetch countries'))
      })
    )
  }

  public saveSettings(settings: Settings, page: string): Observable<any> {
    return this.http
      .post<Settings>(`/api/session-settings/${page}`, settings)
      .pipe(
        catchError((err) => {
          console.error('Error saving settings: ', err)
          return throwError(() => new Error('Failed to save settings'))
        })
      )
  }

  public loadSettings(page: string): Observable<Settings> {
    return this.http.get<Settings>(`/api/session-settings/${page}`).pipe(
      catchError((err) => {
        console.error('Error loading settings: ', err)
        return throwError(() => new Error('Failed to load settings'))
      })
    )
  }

  private parseOecdData(data: OECDData[]): (ChartDataModel | null)[] {
    return data.map((dataPoint) =>
      dataPoint.year !== null && dataPoint.value !== null
        ? { x: dataPoint.year, y: dataPoint.value }
        : null
    )
  }

  private parseWhoData(data: WhoData[]): (ChartDataModel | null)[] {
    return data.map((dataPoint) =>
      dataPoint.NumericValue !== null && dataPoint.TimeDim !== null
        ? {
            x: dataPoint.TimeDim,
            y: dataPoint.NumericValue,
          }
        : null
    )
  }

  private convertToUnix(value: string): string {
    return (new Date(value).getTime() / 1000).toString()
  }
}
