import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Card } from '../model/card/card';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';



@Injectable({
  providedIn: 'root'
})
export class CardService {

  private projectAppUrl = environment.projectAppUrl;

  constructor(private http: HttpClient) { }

  updateCard(card: Card): Observable<Card> {
    const headers = new HttpHeaders({'Content-Type': 'application/json' });
    const options = { headers };
    return this.http.put<Card>(
      this.projectAppUrl + '/tasks/' + card.id,
      card,
      options);
  }

  getCardById(id: string): Observable<Card> {
    return this.http.get<Card>(this.projectAppUrl + '/tasks/' + id);
  }
}
