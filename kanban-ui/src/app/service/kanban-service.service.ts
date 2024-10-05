import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Project } from '../model/project/project';
import { Card } from '../model/card/card';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private projectAppUrl = environment.projectAppUrl;

  constructor(private http: HttpClient) { }

  retrieveAllProjectBoards(): Observable<Project[]> {
    return this.http.get<Project[]>(this.projectAppUrl + '/kanbans/');
  }

  retrieveProjectById(id: string): Observable<Project> {
    return this.http.get<Project>(this.projectAppUrl + '/kanbans/' + id);
  }

  saveNewProject(name: string): Observable<string> {
    const headers = new HttpHeaders({'Content-Type': 'application/json' });
    const options = { headers };
    const jsonObject = this.prepareNameJsonObject(name);
    return this.http.post<string>(
      this.projectAppUrl + '/kanbans/',
      jsonObject,
      options
    );
  }

  saveNewCardInProject(projectId: string, card: Card): Observable<Card> {
    const headers = new HttpHeaders({'Content-Type': 'application/json' });
    const options = { headers };
    return this.http.post<Card>(
      this.projectAppUrl + '/kanbans/' + projectId + '/tasks/',
      card,
      options);
  }

  private prepareNameJsonObject(name: string) {
    const object = {
      name
    };
    return JSON.stringify(object);
  }

}
