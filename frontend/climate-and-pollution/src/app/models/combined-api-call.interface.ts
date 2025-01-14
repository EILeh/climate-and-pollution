import { Observable } from 'rxjs'
import { ChartDataModel } from './chart-data.interface'

export interface ChartDataApiResponse {
  air15?: Observable<(ChartDataModel | null)[]>
  air43?: Observable<(ChartDataModel | null)[]>
  air41?: Observable<(ChartDataModel | null)[]>
  air35?: Observable<(ChartDataModel | null)[]>
  air11?: Observable<(ChartDataModel | null)[]>
  cco2?: Observable<(ChartDataModel | null)[]>
  sdgpm25?: Observable<(ChartDataModel | null)[]>
  sdgairboda?: Observable<(ChartDataModel | null)[]>
  poppollutingfuels?: Observable<(ChartDataModel | null)[]>
  mortality?: Observable<(ChartDataModel | null)[]>
  xTemp?: Observable<(ChartDataModel | null)[]>
  drought?: Observable<(ChartDataModel | null)[]>
}
