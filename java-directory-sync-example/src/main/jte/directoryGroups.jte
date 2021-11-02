@param com.workos.directorysync.models.DirectoryGroupList directoryGroups
@param String directoryId

!{var before = directoryGroups.listMetadata.before;}
!{var after = directoryGroups.listMetadata.after;}

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<style>
  tr {
    line-height: 40px
  }
</style>

<div class="container" style="padding: 40px;">
  <h1>Directory Groups</h1>

  <a class="btn btn-link" href="/directories">Back to Directories</a>

  <table class="table">
    <tr>
      <th>Directory Group ID</th>
      <th>Directory Id</th>
      <th>Group Name</th>
    </tr>
    @for(var directoryGroup : directoryGroups.data)
      <tr>
        <td><a href="/directories/${directoryId}/groups/${directoryGroup.id}">${directoryGroup.id}</a></td>
        <td>${directoryGroup.directoryId}</td>
        <td>${directoryGroup.name}</td>
      </tr>
    @endfor
  </table>
  @if(after != null)
    <a class="btn btn-primary" href="/directories/${directoryId}/groups?after=${after}">Before</a>
  @endif
  @if(before != null)
    <a class="btn btn-primary" href="/directories/${directoryId}/groups?before=${before}">Next</a>
  @endif
</div>
