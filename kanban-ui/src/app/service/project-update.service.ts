import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectUpdateService {
  private updateProjectList = new Subject<void>();

  updateProjectList$ = this.updateProjectList.asObservable();

  updateList() {
    this.updateProjectList.next();
  }
}
